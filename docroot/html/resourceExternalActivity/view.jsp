<%@page import="com.liferay.lms.model.Course"%>
<%@page import="com.liferay.lms.service.CourseLocalServiceUtil"%>
<%@page import="com.tls.lms.util.LiferaylmsUtil"%>
<%@page import="com.liferay.portal.kernel.exception.SystemException"%>
<%@page import="com.liferay.portal.kernel.exception.PortalException"%>
<%@page import="com.liferay.portlet.asset.NoSuchEntryException"%>
<%@page import="com.liferay.portal.security.permission.PermissionCheckerFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.util.PropsKeys"%>
<%@page import="com.liferay.portal.kernel.util.PropsUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.util.DLUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.util.VideoProcessorUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.util.PDFProcessorUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.util.ImageProcessorUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.util.AudioProcessorUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileVersion"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.repository.model.FileEntry"%>
<%@page import="com.liferay.portal.kernel.xml.Element"%>
<%@page import="com.liferay.portal.kernel.xml.SAXReaderUtil"%>
<%@page import="com.liferay.portal.kernel.xml.Document"%>
<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileVersion"%>
<%@page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.documentlibrary.model.DLFileEntry"%>
<%@page import="com.liferay.portlet.journal.model.JournalArticle"%>
<%@page import="com.liferay.portal.service.ServiceContextFactory"%>
<%@page import="com.liferay.portal.service.ServiceContext"%>
<%@page import="com.liferay.lms.service.LearningActivityTryLocalServiceUtil"%>
<%@page import="com.liferay.lms.model.LearningActivityTry"%>
<%@page import="com.liferay.lms.service.LearningActivityResultLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetRendererFactory"%>
<%@page import="com.liferay.portlet.asset.model.AssetRenderer"%>
<%@page import="com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil"%>
<%@page import="com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.portlet.asset.model.AssetEntry"%>
<%@page import="com.liferay.lms.model.LearningActivity"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.lms.service.LearningActivityLocalServiceUtil"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%> 
<%@page import="java.text.DecimalFormat"%>

<%@ include file="/init.jsp" %>
<script src="/liferaylms-portlet/js/service.js" type="text/javascript"></script>
<%@ include file="/html/shared/isTablet.jsp" %>
<%
long actId=ParamUtil.getLong(request,"actId",0);

Boolean isLinkTabletResourceExternal = ParamUtil.getBoolean(request, "isTablet", false);
String cssLinkTabletClassResourceExternal="";
if(isLinkTabletResourceExternal){
	cssLinkTabletClassResourceExternal="tablet-link";
}

if(actId==0 )
{
	
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
}
else
{

	LearningActivity learnact=LearningActivityLocalServiceUtil.getLearningActivity(ParamUtil.getLong(request,"actId"));
	
	%> 
		<h2 class="description-title"><%=learnact.getTitle(themeDisplay.getLocale())%></h2>
		<%--<h3 class="description-h3"><liferay-ui:message key="description" /></h3> --%>
		<div class="description"><%=learnact.getDescriptionFiltered(themeDisplay.getLocale(),true) %></div>
		
	<%
	
	if(learnact.getTypeId()!=2 )
	{
		
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.FALSE);
	}
	else
	{
	
		Course course = CourseLocalServiceUtil.getCourseByGroupCreatedId(learnact.getGroupId());
		boolean hasPermissionAccessCourseFinished = LiferaylmsUtil.hasPermissionAccessCourseFinished(themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), course.getCourseId(), themeDisplay.getUserId());
		boolean hasAccessLock = CourseLocalServiceUtil.canAccessLock(themeDisplay.getScopeGroupId(), user);
		
		if(learnact.canAccess(true, themeDisplay.getUser(), themeDisplay.getPermissionChecker(), hasAccessLock, course, hasPermissionAccessCourseFinished)){
		
			boolean isDefaultScore = true;
			boolean isYoutubeIframe = false;
			boolean isVimeoIframe = false;
			double videoPosition=0;
			int oldScore=0;
			int plays=0;
			LearningActivityTry learningTry = null;
			
			isDefaultScore = (learnact.getPasspuntuation() == 0);
			if(learnact.getExtracontent()!=null &&!learnact.getExtracontent().trim().equals("")){
				if(!Validator.isNumber(learnact.getExtracontent())){
					Document document = SAXReaderUtil.read(learnact.getExtracontent());
					Element root=document.getRootElement();
					Element video=root.element("video");
					if(video!=null)
					{
						if(video.attributeValue("id","").equals(""))
						{
							String youtubeIframeCode= video.getText();
							isYoutubeIframe = ((youtubeIframeCode.indexOf("iframe")>-1) &&  (youtubeIframeCode.indexOf("youtube")>-1));
							isVimeoIframe = ((youtubeIframeCode.indexOf("iframe")>-1) &&  (youtubeIframeCode.indexOf("vimeo")>-1));
						}
					}
				}
			}
			
			if ((isYoutubeIframe || isVimeoIframe) && !isDefaultScore){
				if(!hasPermissionAccessCourseFinished){
					learningTry =LearningActivityTryLocalServiceUtil.getLastLearningActivityTryByActivityAndUser(actId,themeDisplay.getUserId());
				}
				if (learningTry != null){
					//Poner posición del video.
					String xml = learningTry.getTryResultData();
					if(!xml.equals("")){
	
						Document document = SAXReaderUtil.read(xml);
						Element rootElement = document.getRootElement();
						Element positionElement = rootElement.element("position");						
						videoPosition =  Double.parseDouble(positionElement.getText());
						
						Element playsElement = rootElement.element("plays");	
						plays =  Integer.parseInt(playsElement.getText());
	
						Element scoreElement = rootElement.element("score");	
						oldScore =  Integer.parseInt(scoreElement.getText());			
					}	
					
				}
			}	
					
			if(!hasPermissionAccessCourseFinished && !LearningActivityResultLocalServiceUtil.userPassed(actId,themeDisplay.getUserId()))
			{
				if(!permissionChecker.hasPermission(learnact.getGroupId(), LearningActivity.class.getName(), actId, ActionKeys.UPDATE) ||
						!permissionChecker.hasOwnerPermission(learnact.getCompanyId(), LearningActivity.class.getName(), actId, learnact.getUserId(), ActionKeys.UPDATE)){
				
					ServiceContext serviceContext = ServiceContextFactory.getInstance(LearningActivityTry.class.getName(), renderRequest);
		
					if (isDefaultScore){				
						learningTry =LearningActivityTryLocalServiceUtil.createLearningActivityTry(actId,serviceContext);
						learningTry.setEndDate(new java.util.Date(System.currentTimeMillis()));
						learningTry.setResult(100);
						LearningActivityTryLocalServiceUtil.updateLearningActivityTry(learningTry);	
					}
					%>
					<script type="text/javascript">
					
					document.addEventListener( "DOMContentLoaded", function(){
						Liferay.Portlet.refresh('#p_p_id_activityNavigator_WAR_liferaylmsportlet_');
						Liferay.Portlet.refresh('#p_p_id_lmsactivitieslist_WAR_liferaylmsportlet_');
					}, false );		
					
					</script>
					<%
				}
	
			}		
			if(learnact.getExtracontent()!=null &&!learnact.getExtracontent().trim().equals("") )
			{
				
				if(Validator.isNumber(learnact.getExtracontent()))
				{
					long entryId=Long.valueOf(learnact.getExtracontent());
						AssetEntry entry=AssetEntryLocalServiceUtil.getEntry(entryId);
						AssetRendererFactory assetRendererFactory=AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName());			
						AssetRenderer assetRenderer= AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(entry.getClassName()).getAssetRenderer(entry.getClassPK());
						String path = assetRenderer.render(renderRequest, renderResponse, AssetRenderer.TEMPLATE_FULL_CONTENT);
					
						if(entry.getClassName().equals(DLFileEntry.class.getName()))
						{
							
							FileEntry fileEntry=DLAppLocalServiceUtil.getFileEntry(entry.getClassPK());
							long fileEntryId = fileEntry.getFileEntryId();
							long folderId = fileEntry.getFolderId();
							String extension = fileEntry.getExtension();
							String title = fileEntry.getTitle();
		
							FileVersion fileVersion = fileEntry.getFileVersion();
							long fileEntryTypeId = 0;
							if (fileVersion.getModel() instanceof DLFileVersion) {
								DLFileVersion dlFileVersion = (DLFileVersion)fileVersion.getModel();
		
								fileEntryTypeId = dlFileVersion.getFileEntryTypeId();
							}
							boolean hasAudio = AudioProcessorUtil.hasAudio(fileVersion);
							boolean hasImages = ImageProcessorUtil.hasImages(fileVersion);
							boolean hasPDFImages = PDFProcessorUtil.hasImages(fileVersion);
							boolean hasVideo = VideoProcessorUtil.hasVideo(fileVersion);
							int previewFileCount = 0;
							String previewFileURL = null;
							String[] previewFileURLs = null;
							String videoThumbnailURL = null;
		
							String previewQueryString = null;
		
							if (hasAudio) {
								previewQueryString = "&audioPreview=1";
							}
							else if (hasImages) {
								previewQueryString = "&imagePreview=1";
							}
							else if (hasPDFImages) {
								previewFileCount = PDFProcessorUtil.getPreviewFileCount(fileVersion);
		
								previewQueryString = "&previewFileIndex=";
		
								previewFileURL = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, previewQueryString);
							}
							else if (hasVideo) {
								previewQueryString = "&videoPreview=1";
		
								videoThumbnailURL = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&videoThumbnail=1");
							}
		
							if (Validator.isNotNull(previewQueryString)) {
								if (hasAudio) {
									previewFileURLs = new String[PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS).length];
		
									for (int i = 0; i < PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS).length; i++) {
										previewFileURLs[i] = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, previewQueryString + "&type=" + PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PREVIEW_AUDIO_CONTAINERS)[i]);
									}
								}
								else if (hasVideo) {
									if (PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS).length > 0) {
										previewFileURLs = new String[PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS).length];
		
										for (int i = 0; i <PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS).length; i++) {
											previewFileURLs[i] = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, previewQueryString + "&type=" + PropsUtil.getArray(PropsKeys.DL_FILE_ENTRY_PREVIEW_VIDEO_CONTAINERS)[i]);
										}
									}
									else {
										previewFileURLs = new String[1];
		
										previewFileURLs[0] = videoThumbnailURL;
									}
								}
								else {
									previewFileURLs = new String[1];
		
									previewFileURLs[0] = DLUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, previewQueryString);
								}
		
								previewFileURL = previewFileURLs[0];
		
								if (!hasPDFImages) {
									previewFileCount = 1;
								}
							}
		
							request.setAttribute("view_file_entry.jsp-supportedAudio", String.valueOf(hasAudio));
							request.setAttribute("view_file_entry.jsp-supportedVideo", String.valueOf(hasVideo));
		
							request.setAttribute("view_file_entry.jsp-previewFileURLs", previewFileURLs);
							request.setAttribute("view_file_entry.jsp-videoThumbnailURL", videoThumbnailURL);
							%>
							<c:choose>
									<c:when test="<%= previewFileCount == 0 %>">
										<c:if test="<%= AudioProcessorUtil.isAudioSupported(fileVersion) || ImageProcessorUtil.isImageSupported(fileVersion) || PDFProcessorUtil.isDocumentSupported(fileVersion) || VideoProcessorUtil.isVideoSupported(fileVersion) %>">
											<div class="portlet-msg-info">
												<liferay-ui:message key="generating-preview-will-take-a-few-minutes" />
											</div>
										</c:if>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="<%= hasAudio %>">
												<div class="lfr-preview-audio" id="<portlet:namespace />previewFile">
													<div class="lfr-preview-audio-content" id="<portlet:namespace />previewFileContent"></div>
												</div>
		
												<liferay-util:include page="/html/portlet/document_library/player.jsp" />
											</c:when>
											<c:when test="<%= hasImages %>">
												<div class="lfr-preview-file lfr-preview-image" id="<portlet:namespace />previewFile">
													<div class="lfr-preview-file-content lfr-preview-image-content" id="<portlet:namespace />previewFileContent">
														<div class="lfr-preview-file-image-current-column">
															<div class="lfr-preview-file-image-container">
																<img class="lfr-preview-file-image-current" src="<%= previewFileURL %>" />
															</div>
														</div>
													</div>
												</div>
											</c:when>
											<c:when test="<%= hasVideo %>">
												<div class="lfr-preview-file lfr-preview-video" id="<portlet:namespace />previewFile">
													<div class="lfr-preview-file-content lfr-preview-video-content">
														<div class="lfr-preview-file-video-current-column">
															<div id="<portlet:namespace />previewFileContent"></div>
														</div>
													</div>
												</div>
		
												<liferay-util:include page="/html/portlet/document_library/player.jsp" />
											</c:when>
											<c:otherwise>
												<div class="lfr-preview-file" id="<portlet:namespace />previewFile">
													<div class="lfr-preview-file-content" id="<portlet:namespace />previewFileContent">
														<div class="lfr-preview-file-image-current-column">
															<div class="lfr-preview-file-image-container">
																<img class="lfr-preview-file-image-current" id="<portlet:namespace />previewFileImage" src="<%= previewFileURL + "1" %>" />
															</div>
															<span class="lfr-preview-file-actions aui-helper-hidden" id="<portlet:namespace />previewFileActions">
																<span class="lfr-preview-file-toolbar" id="<portlet:namespace />previewToolbar"></span>
		
																<span class="lfr-preview-file-info">
																	<span class="lfr-preview-file-index" id="<portlet:namespace />previewFileIndex">1</span> of <span class="lfr-preview-file-count"><%= previewFileCount %></span>
																</span>
															</span>
														</div>
		
														<div class="lfr-preview-file-images" id="<portlet:namespace />previewImagesContent">
															<div class="lfr-preview-file-images-content"></div>
														</div>
													</div>
												</div>
		
												<aui:script use="aui-base,liferay-preview">
													new Liferay.Preview(
														{
															actionContent: '#<portlet:namespace />previewFileActions',
															baseImageURL: '<%= previewFileURL %>',
															boundingBox: '#<portlet:namespace />previewFile',
															contentBox: '#<portlet:namespace />previewFileContent',
															currentPreviewImage: '#<portlet:namespace />previewFileImage',
															imageListContent: '#<portlet:namespace />previewImagesContent',
															maxIndex: <%= previewFileCount %>,
															previewFileIndexNode: '#<portlet:namespace />previewFileIndex',
															toolbar: '#<portlet:namespace />previewToolbar'
														}
													).render();
												</aui:script>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</div>			<%
						}
						else
						{
					%>
						<liferay-util:include page="<%= path %>" portletId="<%= assetRendererFactory.getPortletId() %>" />
						<%
						}
				}
				else
				{
	
				Document document = SAXReaderUtil.read(learnact.getExtracontent());
				Element root=document.getRootElement();
				Element video=root.element("video");
				if(video!=null)
				{
					if(!video.attributeValue("id","").equals(""))
					{
					AssetEntry videoAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(video.attributeValue("id")));
					DLFileEntry videofile=DLFileEntryLocalServiceUtil.getDLFileEntry(videoAsset.getClassPK());
					DLFileVersion videofileVersion = videofile.getFileVersion();
					String videoURL=themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + videofileVersion.getGroupId() + StringPool.SLASH + videofileVersion.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(videofileVersion.getTitle()));
					
					%>
				<div class="video">
					
	 	<embed type="application/x-shockwave-flash" src="<%=request.getContextPath()%>/flash/flvplayer/playervideo.swf" 
				  width="560" height="315" style="undefined" id="cab" name="cab" bgcolor="#FFFFFF" quality="high" allowfullscreen="true" allowscriptaccess="always" wmode="transparent" menu="false" 
				  flashvars="file=<%=videoURL%>&type=flv" />
				  <%-- 
	 			<video width="320" height="240" controls="controls">
	  					<source src="<%=videoURL %>" type="video/mp4">
	  					</video>
	--%>
			</div>
					<%
					}
					else
					{
						boolean videoControlDisabled = false;
						Element videoControl=root.element("video-control");
						if(videoControl!=null){
							videoControlDisabled = Boolean.parseBoolean(videoControl.getText());
						}
						String videoCode= video.getText();
						if (isYoutubeIframe && !isDefaultScore){
							int delimitador = videoCode.indexOf("iframe") + "iframe".length();
							String partePrimera = videoCode.substring(0, delimitador);
							String parteSegunda = videoCode.substring(delimitador, videoCode.length());
							String parteTercera = parteSegunda.substring(parteSegunda.indexOf("src=\""));
							
							String[] split = parteTercera.split("\"");
							String src = split[1];
							String parametros = ((src.indexOf("?")> -1) ? "&enablejsapi=1" : "?enablejsapi=1");
							if (videoControlDisabled){
								parametros += "&controls=0";
							}
							if (videoPosition > 0 && videoPosition<100){
								DecimalFormat df = new DecimalFormat("#####");
								parametros += "&start="+df.format(videoPosition);
							}
							parteSegunda = parteSegunda.replace(src, src.concat(parametros));
							StringBuilder tag  = new StringBuilder();
							tag.append(partePrimera);
							tag.append(" id=\"youtube-video\"");
							tag.append(parteSegunda);
							videoCode = tag.toString();
						}
						if (isVimeoIframe && !isDefaultScore){
					
							int delimitador = videoCode.indexOf("iframe") + "iframe".length();
							String partePrimera = videoCode.substring(0, delimitador);
							String parteSegunda = videoCode.substring(delimitador, videoCode.length());
							String parteTercera = parteSegunda.substring(parteSegunda.indexOf("src=\""));
							
							String[] split = parteTercera.split("\"");
							String src = split[1];
							String parametros = "?api=1&amp;player_id=player_1";
							parteSegunda = parteSegunda.replace(src, src.concat(parametros));
							StringBuilder tag  = new StringBuilder();
							tag.append(partePrimera);
							tag.append(" id=\"player_1\"");
							tag.append(parteSegunda);
							videoCode = tag.toString();
						} 
					%>
							<div class="video">
							<%=videoCode%>
							</div>
						<%
						if (isYoutubeIframe && !isDefaultScore){
						%>
							<script>
							  // 2. This code loads the IFrame Player API code asynchronously.
							  var plays = <%=plays%>;
							  var tag = document.createElement('script');
							  var finished = false;
	
							  tag.src = "https://www.youtube.com/iframe_api";
							  var firstScriptTag = document.getElementsByTagName('script')[0];
							  firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
	
							  // 3. This function creates an <iframe> (and YouTube player)
							  //    after the API code downloads.
							  var player;
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
									var serviceParameterTypes = [
			 					     	'long',
			 					     	'long',
			 					     	'int',
			 					     	'double',
			 					    	'int'
			 					    ];
			 					    var message = Liferay.Service.Lms.LearningActivityTry.createLearningActivityTry(
			 					    	{
			 					    		actId: <%= actId %>,
			 					    		userId: <%= themeDisplay.getUserId() %>,
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
			 						}						
								}
							  }
							  /*
							  function stopVideo() {
								player.stopVideo();
							  }
							  */
							  var unloadEvent = function (e) {
									var isDefaultScore = <%=isDefaultScore%>;
									var positionToSave = <%=videoPosition%>;
									var oldScore = <%=oldScore%>;
									var currentTime = player.getCurrentTime();
									if (currentTime > positionToSave)
										positionToSave = currentTime;
									var duration = player.getDuration();
									var score = 100;								
									if (!isDefaultScore) score = Math.round((currentTime/duration)*100);
									var serviceParameterTypes = [
								     	'long',
								     	'long',
								     	'int',
								     	'double',
								    	'int'
								    ];
									
									if ((score > oldScore) && !finished){
										var message = Liferay.Service.Lms.LearningActivityTry.createLearningActivityTry(
											{
												actId: <%= actId %>,
												userId: <%= themeDisplay.getUserId() %>,
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
						<%
						}
						if (isVimeoIframe && !isDefaultScore){
							int seekTo = 0;
							if (videoPosition > 0 && videoPosition<100){
								DecimalFormat df = new DecimalFormat("#####");
								seekTo = Integer.parseInt(df.format(videoPosition));
							}
						%>
							<script src="/liferaylms-portlet/js/froogaloop.min.js"></script>
					        <script>
					           // $(function(){
							   document.addEventListener('DOMContentLoaded', function() { 		
									var iframe = document.getElementById('player_1');
									var player = $f(iframe);
									var plays = <%=plays%>;
									var duration = 0;
									var currentTime = 0;
									var seekTo = <%=seekTo %>;
									var finished = false;
									
									 player.addEvent('ready', function() {
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
										plays++;
									}	
				
									function onFinish() {								
										var serviceParameterTypes = [
				 					     	'long',
				 					     	'long',
				 					     	'int',
				 					     	'double',
				 					    	'int'
				 					    ];
				 					    var message = Liferay.Service.Lms.LearningActivityTry.createLearningActivityTry(
				 					    	{
				 					    		actId: <%= actId %>,
				 					    		userId: <%= themeDisplay.getUserId() %>,
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
				 							
				 						}									
									}
									
									  var unloadEvent = function (e) {
										  	
										player.api('getCurrentTime', function(time) {
											currentTime = time;
												
											var isDefaultScore = <%=isDefaultScore%>;
											var positionToSave = <%=videoPosition%>;
											var oldScore = <%=oldScore%>;
											if (currentTime > positionToSave)
												positionToSave = currentTime;
											var score = 100;														
											if (!isDefaultScore) score = Math.round((currentTime/duration)*100);
											//debugger;
											var serviceParameterTypes = [
										     	'long',
										     	'long',
										     	'int',
										     	'double',
										    	'int'
										    ];
											if ((score > oldScore) && !finished){
												var message = Liferay.Service.Lms.LearningActivityTry.createLearningActivityTry(
													{
														actId: <%= actId %>,
														userId: <%= themeDisplay.getUserId() %>,
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
										    } 			
										});	
										  
	
									  };
									  window.addEventListener("beforeunload", unloadEvent);										
								
							  });
					          //  });
					        </script>					
						<%	
						}
					}
				}
				%>
				<div class="container_files">
				<%
				Element documento=null;
				int i = 0;
				do{
					String documentt = "document";
					if(i>0){
						documentt = documentt+(i-1);
					}
				
					documento=root.element(documentt);
					if(documento!=null)
					{
						if(!documento.attributeValue("id","").equals(""))
						{
						try{	
							AssetEntry docAsset= AssetEntryLocalServiceUtil.getAssetEntry(Long.parseLong(documento.attributeValue("id")));
							DLFileEntry docfile=DLFileEntryLocalServiceUtil.getDLFileEntry(docAsset.getClassPK());
							DLFileVersion docfileVersion = docfile.getFileVersion();
							
							String docURL=themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + docfileVersion.getGroupId() + StringPool.SLASH + docfileVersion.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(docfileVersion.getTitle()));
							%>
							<div class="row_file">
							<span class="upfile"><a href="<%=docURL%>" class="<%=cssLinkTabletClassResourceExternal%>" target="_blank"><img class="dl-file-icon" src="<%= themeDisplay.getPathThemeImages() %>/file_system/small/<%= docfileVersion.getIcon() %>.png" /><liferay-ui:message key="resourceexternalactivity.downloadFile"  arguments="<%=new Object[]{HtmlUtil.escape(docfileVersion.getTitle())} %>" /></a></span>
							</div>
							<%
						}catch(NoSuchEntryException nsee){}
						}
					}
					i++;
				}while(documento!=null);
	
				%>
				</div>
				<%
				}
			
			}
		}
	}
}
%>
</div>
